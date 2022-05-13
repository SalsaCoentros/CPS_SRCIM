package Product;

import Resource.ResourceAgent;
import Utilities.Constants;
import jade.core.AID;
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
        System.out.println(myAgent.getLocalName() + ": " + inform.getSender().getLocalName() + " has DONE the skill " + ((ProductAgent)myAgent).currentSkill);
        if (((ProductAgent)myAgent).currentSkill.equals(Constants.SK_DROP)) { //create the message to tell the resource that does the drop that it is free
            ((ProductAgent)myAgent).msgInformRes.clearAllReplyTo();
            ((ProductAgent)myAgent).msgInformRes.clearAllReceiver();
            ((ProductAgent) myAgent).msgInformRes.addReceiver(new AID(((ProductAgent)myAgent).currentSkillReservedFrom,false));
            ((ProductAgent) myAgent).msgInformRes.setContent("true");
        }
    }
}

