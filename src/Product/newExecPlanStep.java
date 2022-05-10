package Product;

import jade.core.behaviours.SimpleBehaviour;

public class newExecPlanStep extends SimpleBehaviour {

    String skill;
    public newExecPlanStep (String skill){
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
        ((ProductAgent)myAgent).skillDone = false;

    }

    @Override
    public boolean done() {
        block(1000);
        return true;
    }
}
