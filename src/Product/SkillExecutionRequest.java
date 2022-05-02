package Product;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.proto.AchieveREInitiator;
import jade.proto.ContractNetInitiator;

import java.util.Vector;


public class SkillExecutionRequest extends AchieveREInitiator{

    public SkillExecutionRequest(Agent a, ACLMessage msg){

        super(a, msg);


    }

    @Override
    protected void handleAgree(ACLMessage agree){
        System.out.println(myAgent.getLocalName() + ": AGREE message received");
    }

    @Override
    protected void handleInform(ACLMessage inform){
        System.out.println(myAgent.getLocalName() + ": INFORM message received");
    }
}

