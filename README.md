# Nextflow Teams Notifier 🚀

A lightweight, modular notification utility for **Nextflow DSL2** pipelines. This tool automatically sends execution summaries to **Microsoft Teams** using beautiful **Adaptive Cards**.

## 🌟 Key Features
* **Clean Codebase**: Decouples notification logic into the `lib/` directory.
* **Security First**: Supports `.env` files to keep Webhook URLs out of source control.
* **Dynamic Styling**: Automatically toggles card colors (Green for Success, Red for Failure).
* **Zero Dependencies**: Built with pure Groovy/Nextflow logic.

---

## 📂 Project Structure
Maintain the following directory layout for the tool to function correctly:

```text
your-project/
├── main.nf              # Your main pipeline script
├── .env                 # Secret variables (DO NOT commit to Git)
├── .env.example         # Template for environment variables
├── .gitignore           # Configured to ignore .env and work/ directories
└── lib/
    └── TeamsNotifier.groovy  # The core notification logic
```
---
## 🛠️ Quick Start 
* **Configuration**

Create a .env file in your project root and add your Teams Webhook URL:
```bash
teams_webhook_url=[https://outlook.office.com/webhook/your_secret_key_here]
```
* **Integration**

Add the following onComplete block at the end of your main.nf script:
```nextflow
workflow.onComplete {
    TeamsNotifier.notify(workflow, params)
}
```
* **Execution**

Run your pipeline as usual:
```nextflow
nextflow run main.nf
```
