package de.raidcraft.skills.test;

import de.raidcraft.skills.*;
import org.bukkit.configuration.ConfigurationSection;

@SkillInfo("test")
public class TestSkill extends AbstractSkill implements de.raidcraft.skills.Executable {

    TestSkill(SkillContext context) {
        super(context);
    }

    @Override
    public void load(ConfigurationSection config) {

    }

    @Override
    public void apply() {

    }

    @Override
    public void remove() {

    }

    @Override
    public ExecutionResult execute(ExecutionContext context) throws Exception {

        return success(context);
    }
}
