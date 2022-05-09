package Resource;

import Utilities.Constants;
import jade.core.Agent;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREResponder;
import jade.proto.ContractNetResponder;

public class SkillExecutionResponse extends AchieveREResponder {
    public SkillExecutionResponse(Agent a, MessageTemplate mt) {
        super(a, mt);
    }

    @Override
    protected ACLMessage handleRequest (ACLMessage request) {
        String skill = request.getContent();
        //System.out.println(myAgent.getLocalName() + ": Processing REQUEST message");
        ACLMessage msg = request.createReply();
        msg.setPerformative(ACLMessage.AGREE);
        return msg;
    }

    @Override
    protected ACLMessage prepareResultNotification (ACLMessage request, ACLMessage response) {
        ((ResourceAgent)myAgent).reserved = false;
        System.out.println(myAgent.getLocalName() + ": is startin the skill named " + ((ResourceAgent)myAgent).reservedSkill);

        /*if ((((ResourceAgent)myAgent).reservedSkill).equals(Constants.SK_PICK_UP) || (((ResourceAgent)myAgent).reservedSkill).equals(Constants.SK_DROP)) {
            block(2000);
        }
        else {*/
            ((ResourceAgent) myAgent).myLib.executeSkill(((ResourceAgent) myAgent).reservedSkill);
        //}
        //System.out.println(myAgent.getLocalName() + " has done the skill");
        ACLMessage msg = request.createReply();
        msg.setPerformative(ACLMessage.INFORM);
        return msg;
    }
}
