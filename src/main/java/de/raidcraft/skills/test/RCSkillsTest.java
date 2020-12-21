package de.raidcraft.skills.test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import de.raidcraft.skills.Skill;
import de.raidcraft.skills.SkillsPlugin;
import de.raidcraft.skills.entities.ConfiguredSkill;
import de.raidcraft.skills.entities.PlayerSkill;
import de.raidcraft.skills.entities.SkilledPlayer;
import de.raidcraft.skills.util.RandomString;
import lombok.Getter;

@Getter
public class RCSkillsTest {

    public static RCSkillsTest load() {

        return new RCSkillsTest(MockBukkit.mock(new ServerMock()), MockBukkit.load(SkillsPlugin.class));
    }

    public static void unload() {

        MockBukkit.unmock();
    }

    private final ServerMock server;
    private final SkillsPlugin plugin;

    public RCSkillsTest(ServerMock server, SkillsPlugin plugin) {

        this.server = server;
        this.plugin = plugin;
    }

    public PlayerSkill addSkill(Class<? extends Skill> skillClass) {

        ConfiguredSkill skill = getPlugin().getSkillManager().loadSkill(skillClass);

        PlayerMock player = getServer().addPlayer(new RandomString().nextString());
        player.addAttachment(plugin, SkillsPlugin.BYPASS_ACTIVE_SKILL_LIMIT, true);
        player.addAttachment(plugin, SkillsPlugin.SKILL_PERMISSION_PREFIX + skill.alias(), true);

        return SkilledPlayer.getOrCreate(player)
                .addSkill(skill)
                .playerSkill();
    }
}
