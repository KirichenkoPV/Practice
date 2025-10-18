package Practice_04_10_2025;

import Practice_04_10_2025.behs.Seller;
import Practice_04_10_2025.behs.Bayer;
import Practice_04_10_2025.behs.Utils.DfUtils;
import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MySellerAgent extends Agent {

    @Override
    protected void setup() {
        DfUtils.register(this, "Seller");
        log.info("Я отправляю сообщение");
        this.addBehaviour(new WakerBehaviour(this, (int) Math.random()*1000) {
            @Override
            protected void onWake() {
                super.onWake();
            }
        });
    }
}
