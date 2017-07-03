import org.jenkinsci.plugins.workflow.libs.SCMSourceRetriever;
import org.jenkinsci.plugins.workflow.libs.LibraryConfiguration;
import jenkins.plugins.git.GitSCMSource;

// Configure the Global Pipeline Libraries
def globalPipelineLibraries = { instance, library_name, library_version, git_url, git_branch ->
    def globalLibsDesc = instance.getDescriptor("org.jenkinsci.plugins.workflow.libs.GlobalLibraries")

    SCMSourceRetriever retriever = new SCMSourceRetriever(new GitSCMSource(
            "exoPipeline",
            git_url,
            "",
            "*",
            "",
            false))

    LibraryConfiguration pipeline = new LibraryConfiguration(library_name, retriever)
    pipeline.setDefaultVersion(library_version)
    pipeline.setImplicit(true)
    pipeline.setAllowVersionOverride(true)
    globalLibsDesc.get().setLibraries([pipeline])

    globalLibsDesc.save()
    println "Setting eXo Global Pipeline Library"
}
