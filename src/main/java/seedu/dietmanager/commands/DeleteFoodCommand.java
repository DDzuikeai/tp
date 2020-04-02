package seedu.dietmanager.commands;

import seedu.dietmanager.FoodNutritionInfo;
import seedu.dietmanager.Profile;

import seedu.dietmanager.exceptions.InvalidFormatException;
import seedu.dietmanager.parser.Parser;
import seedu.dietmanager.ui.MessageBank;
import seedu.dietmanager.ui.UI;

public class DeleteFoodCommand extends Command {
    private static final int ARGUMENTS_REQUIRED = 1;
    private String foodName;
    private boolean noDescription;
    private boolean success;

    /**
     * Constructs the Command object.
     *
     * @param command the command prompt entered by the user.
     * @param description the description of the command.
     */

    public DeleteFoodCommand(String command, String description) throws InvalidFormatException {
        super(command);
        this.noDescription = false;

        try {
            String[] descriptionArray = Parser.parseDescription(description, ARGUMENTS_REQUIRED);
            foodName = descriptionArray[0].toLowerCase();
        } catch (NullPointerException e) {
            this.noDescription = true;
        }
    }

    @Override
    public void execute(Profile profile, UI ui) {
        if (!this.noDescription) {
            FoodNutritionInfo foodInfo = FoodNutritionInfo.getInstance();
            this.success = foodInfo.deleteFood(foodName);
        }
        saveResult(profile);
    }

    @Override
    public void saveResult(Profile profile) {
        if (this.noDescription) {
            this.result = MessageBank.NO_DESCRIPTION_MESSAGE;
        } else if (!this.success) {
            this.result = "No need to delete! Referred Food doesn't exist in database";
        } else {
            this.result = String.format("You have just deleted %s from the database.",foodName);
        }
    }
}
