Nextflow Teams Notifier 🚀
A lightweight, modular notification utility for Nextflow DSL2 pipelines. This tool automatically sends execution summaries (status, duration, task statistics) to Microsoft Teams using beautiful Adaptive Cards.

🌟 Key Features
Clean Codebase: Decouples notification logic into the lib/ directory, keeping your main pipeline focused on the analysis.

Security First: Supports .env files and system environment variables to keep your Webhook URLs out of source control.

Dynamic Styling: Automatically toggles card colors based on workflow results (Green for Success, Red for Failure).

Zero Dependencies: Built with pure Groovy/Nextflow logic—no extra plugins required.

📂 Project Structure
To ensure the utility functions correctly, maintain the following directory layout:

Plaintext
your-project/
├── test_notification.nf              # Your main pipeline script
├── .env                 # Secret variables (DO NOT commit to Git)
├── .env.example         # Template for environment variables
├── .gitignore           # Configured to ignore .env and work/ directories
└── lib/
    └── TeamsNotifier.groovy  # The core notification logic
🛠️ Quick Start
1. Configuration
Create a .env file in your project root and add your Teams Webhook URL:

Bash
# .env
teams_webhook_url=https://outlook.office.com/webhook/your_secret_key_here
2. Integration
Add the following onComplete block at the end of your main.nf script:

程式碼片段
// Your processes and workflow definitions...

workflow.onComplete {
    TeamsNotifier.notify(workflow, params)
}
3. Execution
Run your pipeline as usual:

Bash
nextflow run main.nf
🔒 Security Best Practices
Always ensure your .env file is excluded from version control. Your .gitignore should include:

Plaintext
.env
.nextflow*
work/
bin/
📝 Configuration Priority
The tool searches for the teams_webhook_url in the following order:

System Environment: export teams_webhook_url=...

.env File: Values defined in the root .env file.

Nextflow Params: params.teams_webhook_url defined in nextflow.config.

📊 Notification Preview
The card sent to Teams includes:

Status Header: ✅ Pipeline Completed or ⚠️ Pipeline Issues Detected.

Run Name: The unique Nextflow run name (e.g., focused_curie).

Duration: Total execution time.

Task Stats: Detailed count of succeeded vs. failed tasks.

📜 License
This project is licensed under the MIT License. Feel free to use, modify, and distribute it in your own projects.
