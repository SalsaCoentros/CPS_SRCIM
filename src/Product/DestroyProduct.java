package Product;

import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SimpleBehaviour;

public class DestroyProduct extends OneShotBehaviour {


    @Override
    public void action() {
        myAgent.doDelete();
    }

}
