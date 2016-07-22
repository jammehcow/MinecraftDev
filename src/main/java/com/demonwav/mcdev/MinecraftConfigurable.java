package com.demonwav.mcdev;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class MinecraftConfigurable implements Configurable {
    private JPanel panel;
    private JCheckBox showProjectPlatformIconsCheckBox;
    private JCheckBox showEventListenerGutterCheckBox;
    private JCheckBox showChatColorUnderlinesCheckBox;
    private JComboBox<MinecraftSettings.UnderlineType> chatColorUnderlinesComboBox;
    private JCheckBox showChatGutterIconsCheckBox;

    @NotNull private final PropertiesComponent component;

    public MinecraftConfigurable(@NotNull final PropertiesComponent component) {
        this.component = component;
    }

    @Nls
    @Override
    public String getDisplayName() {
        return "Minecraft Development";
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return null;
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        showChatColorUnderlinesCheckBox.addActionListener(e -> setUnderlineBox());

        return panel;
    }

    private void init() {
        for (MinecraftSettings.UnderlineType type : MinecraftSettings.UnderlineType.values()) {
            chatColorUnderlinesComboBox.addItem(type);
        }

        final MinecraftSettings settings = MinecraftSettings.getInstance();

        showProjectPlatformIconsCheckBox.setSelected(settings.isShowProjectPlatformIcons());
        showEventListenerGutterCheckBox.setSelected(settings.isShowEventListenerGutterIcons());
        showChatGutterIconsCheckBox.setSelected(settings.isShowChatColorGutterIcons());
        showChatColorUnderlinesCheckBox.setSelected(settings.isShowChatColorUnderlines());

        chatColorUnderlinesComboBox.setSelectedIndex(settings.getUnderlineTypeIndex());
        setUnderlineBox();
    }

    private void setUnderlineBox() {
        if (showChatColorUnderlinesCheckBox.isSelected()) {
            chatColorUnderlinesComboBox.setEnabled(true);
        } else {
            chatColorUnderlinesComboBox.setEnabled(false);
        }
    }

    @Override
    public boolean isModified() {
        final MinecraftSettings settings = MinecraftSettings.getInstance();

        return showProjectPlatformIconsCheckBox.isSelected() != settings.isShowProjectPlatformIcons() ||
                showEventListenerGutterCheckBox.isSelected() != settings.isShowEventListenerGutterIcons() ||
                showChatGutterIconsCheckBox.isSelected() != settings.isShowChatColorGutterIcons() ||
                showChatColorUnderlinesCheckBox.isSelected() != settings.isShowChatColorUnderlines() ||
                chatColorUnderlinesComboBox.getSelectedItem() != settings.getUnderlineType();
    }

    @Override
    public void apply() throws ConfigurationException {
        final MinecraftSettings settings = MinecraftSettings.getInstance();

        settings.setShowProjectPlatformIcons(showProjectPlatformIconsCheckBox.isSelected());
        settings.setShowEventListenerGutterIcons(showEventListenerGutterCheckBox.isSelected());
        settings.setShowChatColorGutterIcons(showChatGutterIconsCheckBox.isSelected());
        settings.setShowChatColorUnderlines(showChatColorUnderlinesCheckBox.isSelected());
        settings.setUnderlineType((MinecraftSettings.UnderlineType) chatColorUnderlinesComboBox.getSelectedItem());
    }

    @Override
    public void reset() {
        init();
    }

    @Override
    public void disposeUIResources() {
    }
}