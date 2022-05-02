package Resource;

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
    protected ACLMessage handleRequest (ACLMessage request) throws NotUnderstoodException, RefuseException{
        System.out.println(myAgent.getLocalName() + ": Processing REQUEST message");
        ACLMessage msg = request.createReply();
        msg.setPerformative(ACLMessage.AGREE);
        return msg;
    }

    @Override
    protected ACLMessage prepareResultNotification (ACLMessage request, ACLMessage response) throws FailureException{
        ((ResourceAgent)myAgent).reserved = false;
        System.out.println(myAgent.getLocalName() + ": Preparing result of REQUEST");
        block(5000); //mudar para executar a skill
        ACLMessage msg = request.createReply();
        msg.setPerformative(ACLMessage.INFORM);
        return msg;
    }
}
