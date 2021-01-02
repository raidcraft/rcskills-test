package de.raidcraft.skills.test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import de.raidcraft.skills.Skill;
import de.raidcraft.skills.SkillContext;
import de.raidcraft.skills.SkillsPlugin;
import de.raidcraft.skills.entities.ConfiguredSkill;
import de.raidcraft.skills.entities.PlayerSkill;
import de.raidcraft.skills.entities.SkilledPlayer;
import de.raidcraft.skills.util.RandomString;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;

import javax.naming.ldap.Rdn;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.mockito.Mockito.spy;

@Getter
public class RCSkillsTest {

    private final RandomString randomString = new RandomString();
    private ServerMock server;
    private SkillsPlugin plugin;

    @BeforeEach
    public void load() {

        server = MockBukkit.mock();
        plugin = MockBukkit.load(SkillsPlugin.class);
    }

    @AfterEach
    public void unload() {

        MockBukkit.unmock();
    }

    protected SkilledPlayer player() {

        return SkilledPlayer.getOrCreate(server.addPlayer(randomString.nextString()));
    }

    protected ConfiguredSkill load(ConfigurationSection config) {

        return load(TestSkill.class, TestSkill::new, skill -> {
        }, config);
    }

    protected ConfiguredSkill load(Consumer<ConfiguredSkill> skill) {

        return load(TestSkill.class, TestSkill::new, skill, new MemoryConfiguration());
    }

    protected <TSkill extends Skill> ConfiguredSkill load(Class<TSkill> skillClass,
                                                          Function<SkillContext, TSkill> supplier,
                                                          Consumer<ConfiguredSkill> skill,
                                                          ConfigurationSection config) {

        plugin.getSkillManager().registerSkill(skillClass, supplier);

        ConfiguredSkill test = ConfiguredSkill.getOrCreate(UUID.randomUUID())
                .alias(randomString.nextString())
                .type("test");
        skill.accept(test);
        return test.load(config);
    }

    protected PlayerSkill loadAndAdd() {

        return loadAndAdd(skill -> {});
    }

    protected PlayerSkill loadAndAdd(Consumer<ConfiguredSkill> skill) {

        return player().addSkill(load(skill), true).playerSkill();
    }

    protected PlayerSkill addToPlayer(SkilledPlayer player, ConfiguredSkill skill) {

        return player.addSkill(skill, true).playerSkill();
    }

    protected SkillContext loadContext() {

        return loadContext(skill -> {});
    }

    protected SkillContext loadContext(Consumer<ConfiguredSkill> skill) {

        return plugin.getSkillManager().loadSkill(loadAndAdd(skill));
    }

    protected SkillContext loadContext(PlayerSkill skill) {

        return plugin.getSkillManager().loadSkill(skill);
    }
}
