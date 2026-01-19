workflow {
    log.info "Test Teams notify..."
}

workflow.onComplete {
    TeamsNotifier.notify(workflow, params)
}
