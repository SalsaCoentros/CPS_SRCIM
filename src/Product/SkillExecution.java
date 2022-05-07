package Product;

import jade.core.behaviours.FSMBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.proto.AchieveREInitiator;
import jade.proto.ContractNetInitiator;

public class SkillExecution extends SimpleBehaviour {

    AchieveREInitiator b = null;
    boolean aux = true;
    @Override
    public void action() {
        if ( aux ){
            if (((ProductAgent)myAgent).transportDone){
                b = new SkillExecutionRequest(myAgent, ((ProductAgent) myAgent).msgExecuteSkill);
                myAgent.addBehaviour(b);
                aux = false;
            }
        }
    }

    @Override
    public boolean done() {
        return ((ProductAgent)myAgent).skillDone;

    }
}
