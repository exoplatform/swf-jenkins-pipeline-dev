import jenkins.model.*
import hudson.model.*
import hudson.slaves.*
import hudson.plugins.sshslaves.*
import java.util.ArrayList;
import hudson.slaves.EnvironmentVariablesNodeProperty.Entry;

// Configure extended email
def createAgent = { instance, name, label, nb_executors, credentials_id ->

  List<Entry> env = new ArrayList<Entry>();
  EnvironmentVariablesNodeProperty envPro = new EnvironmentVariablesNodeProperty(env);
  Slave slave = new DumbSlave(
          name,"CI Docker Agent",
          "ci-agent",
          nb_executors,
          Node.Mode.NORMAL,
          label,
          new JNLPLauncher(),
          new RetentionStrategy.Always(),
          new LinkedList())
  slave.getNodeProperties().add(envPro)
  Jenkins.instance.addNode(slave)

  println "Create " + name + " agent"
}
