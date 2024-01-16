package com.pongame;

import com.pongame.classes.Player;
import com.pongame.game.Game;
import com.pongame.graphics.HomePage;
import com.pongame.config.DifficultyLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class HomePageTest {

    private HomePage homePage;

    @Mock
    private Player player;

    @Mock
    private JComboBox<DifficultyLevel> difficultyComboBox;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        homePage = new HomePage(player);
        homePage.difficultyComboBox = difficultyComboBox;
    }

    @Test
    public void testDifficultyComboBoxSelection() {
        DifficultyLevel selectedDifficulty = DifficultyLevel.MEDIUM;
        when(difficultyComboBox.getSelectedItem()).thenReturn(selectedDifficulty);
        DifficultyLevel actualDifficulty = (DifficultyLevel) difficultyComboBox.getSelectedItem();
        assertEquals(selectedDifficulty, actualDifficulty);
    }


}
