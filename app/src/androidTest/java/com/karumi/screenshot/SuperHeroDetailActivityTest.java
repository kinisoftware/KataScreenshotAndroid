package com.karumi.screenshot;

import android.app.Activity;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import com.karumi.screenshot.di.MainComponent;
import com.karumi.screenshot.di.MainModule;
import com.karumi.screenshot.model.SuperHero;
import com.karumi.screenshot.model.SuperHeroesRepository;
import com.karumi.screenshot.ui.view.SuperHeroDetailActivity;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import it.cosenonjaviste.daggermock.DaggerMockRule;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class SuperHeroDetailActivityTest extends ScreenshotTest {

    @Rule
    public DaggerMockRule<MainComponent> daggerRule =
            new DaggerMockRule<>(MainComponent.class, new MainModule()).set(
                    new DaggerMockRule.ComponentSetter<MainComponent>() {
                        @Override
                        public void setComponent(MainComponent component) {
                            SuperHeroesApplication app =
                                    (SuperHeroesApplication) InstrumentationRegistry.getInstrumentation()
                                            .getTargetContext()
                                            .getApplicationContext();
                            app.setComponent(component);
                        }
                    });

    @Rule
    public ActivityTestRule<SuperHeroDetailActivity> activityRule =
            new ActivityTestRule<>(SuperHeroDetailActivity.class, true, false);

    @Mock
    SuperHeroesRepository repository;

    @Test
    public void showsSuperHeroInfoCorrectlyForAnAvengerSuperHero() {
        Activity activity = startActivity(givenAnAvenger());

        compareScreenshot(activity);
    }

    @Test
    public void showsSuperHeroInfoCorrectlyForARegularSuperHero() {
        Activity activity = startActivity(givenAnRegularSuperHero());

        compareScreenshot(activity);
    }

    @Test
    public void showsSuperHeroInfoCorrectlyForASuperHeroWithoutDescription() {
        Activity activity = startActivity(givenThereIsASuperHeroWithoutDescription());

        compareScreenshot(activity);
    }

    @Test
    public void showsSuperHeroInfoCorrectlyForASuperHeroWithoutName() {
        Activity activity = startActivity(givenThereIsASuperHeroWithoutName());

        compareScreenshot(activity);
    }

    @Test
    public void showsSuperHeroInfoCorrectlyForASuperHeroWithLongName() {
        Activity activity = startActivity(givenThereIsASuperHeroWithLongName());

        compareScreenshot(activity);
    }

    @Test
    public void showsSuperHeroInfoCorrectlyForASuperHeroWithLongDescription() {
        Activity activity = startActivity(givenThereIsASuperHeroWithDescription());

        compareScreenshot(activity);
    }

    private SuperHero givenAnAvenger() {
        return givenThereIsASuperHero(true);
    }

    private SuperHero givenAnRegularSuperHero() {
        return givenThereIsASuperHero(false);
    }

    private SuperHero givenThereIsASuperHero(boolean isAvenger) {
        String superHeroName = "SuperHero";
        String superHeroDescription = "Super Hero Description";
        SuperHero superHero = new SuperHero(superHeroName, null, isAvenger, superHeroDescription);
        when(repository.getByName(superHeroName)).thenReturn(superHero);
        return superHero;
    }

    private SuperHero givenThereIsASuperHeroWithoutDescription() {
        String superHeroName = "SuperHero";
        SuperHero superHero = new SuperHero(superHeroName, null, false, "");
        when(repository.getByName(superHeroName)).thenReturn(superHero);
        return superHero;
    }

    private SuperHero givenThereIsASuperHeroWithLongName() {
        String superHeroName = "SuperHeroLoooooooonnnnnnnnnnnnnnnnnnnnnnnnnngggggggggggggggggggggg";
        SuperHero superHero = new SuperHero(superHeroName, null, false, "");
        when(repository.getByName(superHeroName)).thenReturn(superHero);
        return superHero;
    }

    private SuperHero givenThereIsASuperHeroWithDescription() {
        String superHeroDescription = "Super Hero Description Tooooooooo " +
                "Looooooooooooooonnnnnnnnnnnnnnnnnnnnnnnnnnnnn adslfkjslfjklsajfkñaskdfjñadjffh" +
                "jasdhflhasdjlfhjkashfjkdhsfhlkshfkaldshflaksdhfakjshfkjlsahfakjsdhfjksdhfkjhidufqw" +
                "eryiuwqeyreiuqhriuqwheuifhqwuiehfkhakjhfdkjabfkjbvbz,mcbvz,mnbvc";

        SuperHero superHero = new SuperHero("", null, false, superHeroDescription);
        when(repository.getByName(anyString())).thenReturn(superHero);
        return superHero;
    }

    private SuperHero givenThereIsASuperHeroWithoutName() {
        String superHeroDescription = "Super Hero Description";
        SuperHero superHero = new SuperHero("", null, false, superHeroDescription);
        when(repository.getByName(anyString())).thenReturn(superHero);
        return superHero;
    }

    private SuperHeroDetailActivity startActivity(SuperHero superHero) {
        Intent intent = new Intent();
        intent.putExtra("super_hero_name_key", superHero.getName());
        return activityRule.launchActivity(intent);
    }
}
