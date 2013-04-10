/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */

package mage.sets.dragonsmaze;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */


public class SinCollector extends CardImpl<SinCollector> {

    public SinCollector(UUID ownerId) {
        super(ownerId, 103, "Sin Collector", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{W}{B}");
        this.expansionSetCode = "DGM";
        this.subtype.add("Human");
        this.subtype.add("Cleric");
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        this.color.setBlack(true);
        this.color.setWhite(true);

        // When Sin Collector enters the battlefield, target opponent reveals his or her hand. You choose an instant or sorcery card from it and exile that card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new SinCollectorEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    public SinCollector(final SinCollector card) {
        super(card);
    }

    @Override
    public SinCollector copy() {
        return new SinCollector(this);
    }
}


class SinCollectorEffect extends OneShotEffect<SinCollectorEffect> {

    private static final FilterCard filter = new FilterCard("instant or sorcery card");
    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)));
    }

    public SinCollectorEffect() {
        super(Outcome.Exile);
        staticText = "target opponent reveals his or her hand. You choose an instant or sorcery card from it and exile that card";
    }

    public SinCollectorEffect(final SinCollectorEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            player.revealCards("Sin Collector", player.getHand(), game);
            Player you = game.getPlayer(source.getControllerId());
            if (you != null) {
                TargetCard target = new TargetCard(Zone.PICK, filter);
                target.setRequired(true);
                if (you.choose(Outcome.Benefit, player.getHand(), target, game)) {
                    Card card = player.getHand().get(target.getFirstTarget(), game);
                    if (card != null) {
                        card.moveToExile(null, null , source.getSourceId(), game);
                    }
                }
            }
        }
        return false;
    }

    @Override
    public SinCollectorEffect copy() {
        return new SinCollectorEffect(this);
    }

}
