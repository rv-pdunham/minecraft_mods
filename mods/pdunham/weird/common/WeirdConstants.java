package pdunham.weird.common;

import net.minecraft.item.EnumArmorMaterial;
import net.minecraftforge.common.EnumHelper;

public class WeirdConstants {

	// addArmorMaterial(textureName, armorDurability, absorbedDamage = { helmet, chest, legs, boots}, enchantibility);
    public static EnumArmorMaterial armorWEIRD = EnumHelper.addArmorMaterial("WEIRD", 40, new int[] {4, 9, 7, 4}, 30);
    
    public static String pathIcons = "/pdunham/weird/weirdIcons.png";
    public static String pathArmor = "/pdunham/weird/weirdArmor.png";
    public static String pathArmorLegs = "/pdunham/weird/weirdArmorLegs.png";

	// Make private so we never actuall make one.
	private WeirdConstants() { }

}