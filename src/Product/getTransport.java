package Product;

import Utilities.DFInteraction;
import jade.core.Agent;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.proto.ContractNetInitiator;

import java.util.Vector;

/*public class initiatorAgent_com  extends Agent {
    @Override
    protected void setup(){
        /*
        new ACLMessage - indicates
        ACLMessage.CFP indicates FIPA ContractNet value 3
        addReceiver -Adds a value to :receiver slot.
        AID Constructor for an Agent-identifier
            Parameters:
                name - is the value for the slot name for the agent.
                isGUID - indicates if the passed name is already a globally unique identifier or not.
         */
        /*ACLMessage msg = new ACLMessage(ACLMessage.CFP);
        msg.addReceiver(new AID("responder", false));
        this.addBehaviour(new initiator(this, msg));
    }*/

    public class getTransport extends ContractNetInitiator {

        DFAgentDescription [] SkilfullAgents = null;
        public getTransport(Agent a, ACLMessage msg){
            super(a, msg);
        }

        @Override
        protected void handleInform(ACLMessage inform){
            System.out.println(myAgent.getLocalName() + ": INFORM message received");
        }
        @Override
        protected void handleAllResponses(Vector responses, Vector acceptances){
            System.out.println(myAgent.getLocalName() + ": ALL PROPOSALS received");
            // get() - Returns the element at the specified position in this Vector.
            //It's necessary to cast the element of a vector
            ACLMessage auxMsg = (ACLMessage) responses.get(0);
            //Set a selection criteria to choose the Agent

            try {
                SkilfullAgents = DFInteraction.SearchInDFByName("sk_move", myAgent);
            }catch(FIPAException e){
                e.printStackTrace();
            }

            ACLMessage reply = auxMsg.createReply();
            //ACLMessage.ACCEPT_PROPOSAL indicates FIPA ContractNet with a zero value
            reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
            // add() - Appends the specified element to the end of this Vector.
            acceptances.add(reply);
        }


    }
//}



