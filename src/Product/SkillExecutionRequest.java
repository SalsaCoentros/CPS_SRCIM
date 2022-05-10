package Product;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.proto.AchieveREInitiator;


public class SkillExecutionRequest extends AchieveREInitiator{

    public SkillExecutionRequest(Agent a, ACLMessage msg){
        super(a, msg);
    }

    @Override
    protected void handleInform(ACLMessage inform){
        ((ProductAgent)myAgent).skillDone = true;
        System.out.println("Skill done");

    }

}

