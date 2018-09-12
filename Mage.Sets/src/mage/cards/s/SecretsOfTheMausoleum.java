package mage.cards.s;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author TheElk801
 */
public final class SecretsOfTheMausoleum extends CardImpl {

    public SecretsOfTheMausoleum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Undergrowth — Search your library for a black card with converted mana cost equal to or less than the number of creature cards in your graveyard, reveal it, put it into your hand, then shuffle your library.
        this.getSpellAbility().addEffect(new SecretsOfTheMausoleumEffect());
    }

    public SecretsOfTheMausoleum(final SecretsOfTheMausoleum card) {
        super(card);
    }

    @Override
    public SecretsOfTheMausoleum copy() {
        return new SecretsOfTheMausoleum(this);
    }
}

class SecretsOfTheMausoleumEffect extends OneShotEffect {

    public SecretsOfTheMausoleumEffect() {
        super(Outcome.Benefit);
        this.staticText = "<i>Undergrowth</i> &mdash; Search your library "
                + "for a black card with converted mana cost less than "
                + "or equal to the number of creature cards in your graveyard, "
                + "reveal it, put it into your hand, then shuffle your library.";
    }

    public SecretsOfTheMausoleumEffect(final SecretsOfTheMausoleumEffect effect) {
        super(effect);
    }

    @Override
    public SecretsOfTheMausoleumEffect copy() {
        return new SecretsOfTheMausoleumEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int critterCount = player.getGraveyard().count(StaticFilters.FILTER_CARD_CREATURE, game);
        FilterCard filter = new FilterCard("a black card with converted mana cost less than or equal to " + critterCount);
        filter.add(new ColorPredicate(ObjectColor.BLACK));
        filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, critterCount + 1));
        return new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true).apply(game, source);
    }
}