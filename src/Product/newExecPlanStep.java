package Product;

import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;

public class newExecPlanStep extends SimpleBehaviour {

    String skill;
    public newExecPlanStep (Agent a, String skill){
        this.skill = skill;
    }

    @Override
    public void action() {
        ((ProductAgent)myAgent).msgExecuteSkill.clearAllReplyTo();
        ((ProductAgent)myAgent).msgExecuteSkill.clearAllReceiver();
        ((ProductAgent)myAgent).cfp.clearAllReceiver();
        ((ProductAgent)myAgent).cfp.clearAllReplyTo();
        ((ProductAgent)myAgent).currentSkill = skill;
        ((ProductAgent)myAgent).skillReserved = false;
    }

    @Override
    public boolean done() {
        block(1000);
        return true;
    }
}
