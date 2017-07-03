import hudson.model.*
import jenkins.model.*
import hudson.slaves.*
import hudson.plugins.sshslaves.*

def e = { filepath ->
    def env = System.getenv()
    evaluate(new File(env["JENKINS_HOME"] + '/init.groovy.d/' + filepath))
}
def admin_email = e("./../init.groovy.mixins/AdminEmail.groovy")
def create_agent = e("./../init.groovy.mixins/CreateAgent.groovy")
def extended_email = e("./../init.groovy.mixins/ExtendedEmail.groovy")
def envvars = e("./../init.groovy.mixins/Envvars.groovy")
def global_pipeline_libs = e("./../init.groovy.mixins/GlobalPipelineLibraries.groovy")
def num_executors = e("./../init.groovy.mixins/NumExecutors.groovy")
def mailer = e("./../init.groovy.mixins/Mailer.groovy")
def set_user = e("./../init.groovy.mixins/SetUser.groovy")
def user_credential = e("./../init.groovy.mixins/UserCredentials.groovy")

def env = System.getenv()
def credentials = (env['JENKINS_USER'] + "-credential")
def j = Jenkins.getInstance()
def home = env["JENKINS_HOME"]

set_user(username='exo-user', fullname='eXo User', email='exo-user@exoplatform.com')


admin_email(
        instance = j,
        admin_addr= env['JENKINS_ADMIN_ADDR']
)
envvars(
        instance    = j,
        env_var_map = [ "LANG": "en_US.UTF-8", "dockerRunParams": env['JENKINS_DOCKER_RUN_PARAMS'] ]
)
num_executors(
        instance      = j,
        num = env["JENKINS_MASTER_EXECUTORS"].toInteger()
)

mailer(
        instance     = j,
        smtp_host    = env['SMTP_HOST'],
        smtp_port    = env['SMTP_PORT'],
        replyto_addr = env['JENKINS_REPLYTO_ADDR'],
        email_suffix = env['JENKINS_EMAIL_SUFFIX']
)
extended_email(
        instance = j
)
user_credential(
        instance = j,
        username = env['JENKINS_USER'],
        password = env['JENKINS_PASSWORD'],
)
global_pipeline_libs(
        instance        = j,
        library_name    = env['PIPELINE_LIB_NAME'],
        library_version = env['PIPELINE_LIB_VERSION'],
        git_url         = env['PIPELINE_LIB_GIT_URL'],
        git_branch      = env['PIPELINE_LIB_GIT_BRANCH']
)
create_agent(
        instance = j,
        name = "ci-agent",
        label = "ci-docker",
        nb_executors = env['JENKINS_AGENT_EXECUTORS'],
        credentials_id = credentials,
)
instance.save()
