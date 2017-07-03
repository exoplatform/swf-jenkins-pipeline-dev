#!groovy

// Triggers configuration
def scmValue = 'H 13 * * 6'
def cronValue = 'H 13 * * 0'

// Jobs list
[
    // mgreau javaee sample project
    [ project: 'maven-sandbox-project', gitOrganization: 'exodev', privacy: 'public', gitBranch: 'develop', dockerImage: 'exoplatform/ci:jdk8-maven33', mavenGoals: 'clean package', deployAtEnd: 'false'],

].each { Map config ->

    def gitUrl = "https://github.com/${config.gitOrganization}/${config.project}.git"

    pipelineJob("${config.project}-${config.gitBranch}-ci") {
      description("<b><span style='color:red'>DO NOT EDIT HERE!</span></b>")

      logRotator(15, 15)
      concurrentBuild(false)

      authorization {
        if(config.privacy.equals('public')){
          permission('hudson.model.Item.Read', 'anonymous')
        }
      }

      environmentVariables {
        keepSystemVariables(true)
        keepBuildVariables(true)
        // Env variables for exoCI Groovy script
        if(config.mavenProfiles != null){
          env('mavenProfiles', "${config.mavenProfiles}")
        }
        if(config.deployAtEnd != null){
          env('deployAtEnd', "${config.deployAtEnd}")
        }
      }

      triggers {
          githubPush()
          scm("${scmValue}")
          cron("${cronValue}")
      }

      definition {
        cps {
          script('''#!groovy
node('ci-docker'){
    exoCI{
        gitUrl = ''' + "'" + "${gitUrl}" + "'" + '''
        gitBranch = ''' + "'" + "${config.gitBranch}" + "'" + '''
        dockerImage = ''' + "'" + "${config.dockerImage}" + "'" + '''
        mavenGoals = ''' + "'" + "${config.mavenGoals}" + "'" + '''
    }
}
''')
        }
      }
    }
}
