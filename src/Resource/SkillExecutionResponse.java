package Resource;

import Utilities.Constants;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREResponder;
import java.util.Collections;
import java.util.List;

public class SkillExecutionResponse extends AchieveREResponder {
    public SkillExecutionResponse(Agent a, MessageTemplate mt) {
        super(a, mt);
    }

    @Override
    protected ACLMessage handleRequest (ACLMessage request) {

        ACLMessage msg = request.createReply();
        String skill = request.getContent();

        if (Collections.frequency(List.of((((ResourceAgent) myAgent).associatedSkills)), skill) == 1) {
            msg.setPerformative(ACLMessage.AGREE);
        } else {
            msg.setPerformative(ACLMessage.REFUSE);
        }
        return msg;
    }

    @Override
    protected ACLMessage prepareResultNotification (ACLMessage request, ACLMessage response) {
        ACLMessage msg = request.createReply();
        System.out.println(request.getSender().getLocalName() + ": " + myAgent.getLocalName() + " is STARTING the skill: " + ((ResourceAgent) myAgent).reservedSkill);
        //((ResourceAgent) myAgent).myLib.executeSkill(((ResourceAgent) myAgent).reservedSkill);
        block(5000);
        msg.setPerformative(ACLMessage.INFORM);
        return msg;
    }
}
