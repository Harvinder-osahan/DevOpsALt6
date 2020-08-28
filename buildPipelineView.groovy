buildPipelineView('DevOpsTask6') {
    filterBuildQueue()
    filterExecutors()
    title('DevOpsTask6 build Pipeline')
    displayedBuilds(1)
    selectedJob('Code-Fetch')
    alwaysAllowManualTrigger()
    showPipelineParameters()
    refreshFrequency(60)
}