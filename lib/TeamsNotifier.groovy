import groovy.json.JsonOutput
import java.nio.file.Path  

class TeamsNotifier {
    private static void loadDotEnv(projectDir) { 
        def envFile = new File("${projectDir}/.env")
        if (envFile.exists()) {
            envFile.eachLine { line ->
                def trimmed = line.trim()
                if (trimmed && !trimmed.startsWith('#') && trimmed.contains('=')) {
                    def parts = trimmed.split('=', 2)
                    if (parts.length == 2) {
                        System.setProperty(parts[0].trim(), parts[1].trim())
                    }
                }
            }
        }
    }

    static void notify(workflow, params) {
        loadDotEnv(workflow.projectDir)

        def url = System.getenv('teams_webhook_url') ?: 
                  System.getProperty('teams_webhook_url') ?: 
                  params.teams_webhook_url

        if (!url) {
            println "Warning: No Teams URL found."
            return
        }

        def isSuccess = workflow.success
        def statusTitle = isSuccess ? "✅ Pipeline Completed" : "⚠️ Pipeline Issues Detected"
        def statusColor = isSuccess ? "Good" : "Attention"
        def message = """
        **Run Name:** ${workflow.runName} \n
        **Status:** ${isSuccess ? 'Success' : 'Failed'} \n
        **Duration:** ${workflow.duration} \n
        **Tasks:** ${workflow.stats.succeedCount} succeeded, ${workflow.stats.failedCount} failed
        """.stripIndent()

        def payload = [
            type: "message",
            attachments: [[
                contentType: "application/vnd.microsoft.card.adaptive",
                content: [
                    type: "AdaptiveCard",
                    version: "1.4",
                    body: [
                        [type: "TextBlock", text: statusTitle, weight: "Bolder", size: "Large", color: statusColor],
                        [type: "TextBlock", text: message, wrap: true]
                    ],
                    '$schema': "http://adaptivecards.io/schemas/adaptive-card.json"
                ]
            ]]
        ]

        try {
            def post = new URL(url).openConnection()
            post.setRequestMethod("POST")
            post.setDoOutput(true)
            post.setRequestProperty("Content-Type", "application/json")
            post.getOutputStream().write(JsonOutput.toJson(payload).getBytes("UTF-8"))
            post.getResponseCode()
        } catch (Exception e) {
            println "Notification failed: ${e.message}"
        }
    }
}
