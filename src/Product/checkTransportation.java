package Product;

import jade.core.behaviours.SequentialBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.proto.ContractNetInitiator;

public class checkTransportation extends SimpleBehaviour {

    ContractNetInitiator B = null;
    boolean finished = false;
    @Override
    public void action() {

        if (((ProductAgent)myAgent).skillReserved) {
            if (((ProductAgent) myAgent).currentLocation.equals(((ProductAgent) myAgent).nextLocation)) { //transport is not needed
                finished = true;
                ((ProductAgent) myAgent).transportDone = true;
                System.out.println("No need for transport from " + ((ProductAgent) myAgent).currentLocation + " to " + ((ProductAgent) myAgent).nextLocation);
            } else {

                B = new getTransport((myAgent), ((ProductAgent) myAgent).cfp);

                SequentialBehaviour sb = new SequentialBehaviour();
                sb.addSubBehaviour(new GetSkillfullAgent(myAgent));
                sb.addSubBehaviour(B);

                myAgent.addBehaviour(sb);

                finished = true;

            }
        }
    }

    @Override
    public boolean done() {
        return finished;
    }
}
