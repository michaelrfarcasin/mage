package mage.abilities.common;

import mage.abilities.EvasionAbility;
import mage.abilities.effects.Effect;
import mage.constants.Zone;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class SimpleEvasionAbility extends EvasionAbility {

    public SimpleEvasionAbility(Effect effect) {
        this(effect, Zone.ALL);
    }

    public SimpleEvasionAbility(Effect effect, Zone zone) {
        super(zone);
        if (effect != null) {
            this.addEffect(effect);
        }
    }

    public SimpleEvasionAbility(SimpleEvasionAbility ability) {
        super(ability);
    }

    @Override
    public SimpleEvasionAbility copy() {
        return new SimpleEvasionAbility(this);
    }

}
