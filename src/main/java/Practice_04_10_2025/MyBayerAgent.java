package Practice_04_10_2025;

import Practice_04_10_2025.behs.Bayer;
import Practice_04_10_2025.behs.Seller;
import Practice_04_10_2025.behs.Utils.DfUtils;
import jade.core.Agent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyBayerAgent extends Agent {

    @Override
    protected void setup() {
        DfUtils.register(this, "Bayer");

        this.addBehaviour(new Bayer());
    }
}
