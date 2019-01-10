package mezz.jei.gui.overlay.bookmarks;

import java.util.List;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.text.TextFormatting;

import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.bookmarks.BookmarkList;
import mezz.jei.config.IWorldConfig;
import mezz.jei.config.KeyBindings;
import mezz.jei.gui.GuiHelper;
import mezz.jei.gui.elements.GuiIconToggleButton;
import mezz.jei.util.Translator;
import org.lwjgl.glfw.GLFW;

public class BookmarkButton extends GuiIconToggleButton {
	public static BookmarkButton create(BookmarkOverlay bookmarkOverlay, BookmarkList bookmarkList, GuiHelper guiHelper, IWorldConfig worldConfig) {
		IDrawableStatic offIcon = guiHelper.getBookmarkButtonDisabledIcon();
		IDrawableStatic onIcon = guiHelper.getBookmarkButtonEnabledIcon();
		return new BookmarkButton(offIcon, onIcon, bookmarkOverlay, bookmarkList, worldConfig);
	}

	private final BookmarkOverlay bookmarkOverlay;
	private final BookmarkList bookmarkList;
	private final IWorldConfig worldConfig;

	private BookmarkButton(IDrawable offIcon, IDrawable onIcon, BookmarkOverlay bookmarkOverlay, BookmarkList bookmarkList, IWorldConfig worldConfig) {
		super(offIcon, onIcon);
		this.bookmarkOverlay = bookmarkOverlay;
		this.bookmarkList = bookmarkList;
		this.worldConfig = worldConfig;
	}

	@Override
	protected void getTooltips(List<String> tooltip) {
		tooltip.add(Translator.translateToLocal("jei.tooltip.bookmarks"));
		KeyBinding bookmarkKey = KeyBindings.bookmark;
		if (bookmarkKey.getKey().getKeyCode() == GLFW.GLFW_KEY_UNKNOWN) {
			tooltip.add(TextFormatting.RED + Translator.translateToLocal("jei.tooltip.bookmarks.usage.nokey"));
		} else if (!bookmarkOverlay.hasRoom()) {
			tooltip.add(TextFormatting.GOLD + Translator.translateToLocal("jei.tooltip.bookmarks.not.enough.space"));
		} else {
			tooltip.add(TextFormatting.GRAY + Translator.translateToLocalFormatted("jei.tooltip.bookmarks.usage.key", bookmarkKey.func_197978_k()));
		}
	}

	@Override
	protected boolean isIconToggledOn() {
		return bookmarkOverlay.isListDisplayed();
	}

	@Override
	protected boolean onMouseClicked(double mouseX, double mouseY, int mouseButton) {
		if (!bookmarkList.isEmpty() && bookmarkOverlay.hasRoom()) {
			worldConfig.toggleBookmarkEnabled();
			return true;
		}
		return false;
	}
}