/*
 * This file is part of EchoPet.
 *
 * EchoPet is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * EchoPet is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with EchoPet.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.dsh105.echopet.nms.v1_7_R1.entity.type;

import com.dsh105.echopet.api.entity.*;
import com.dsh105.echopet.api.entity.nms.type.EntitySkeletonPet;
import com.dsh105.echopet.api.entity.pet.Pet;
import com.dsh105.echopet.api.entity.pet.type.SkeletonPet;
import com.dsh105.echopet.api.plugin.EchoPet;
import com.dsh105.echopet.nms.v1_7_R1.entity.EntityPetImpl;
import net.minecraft.server.v1_7_R1.ItemStack;
import net.minecraft.server.v1_7_R1.Items;
import net.minecraft.server.v1_7_R1.World;
import org.bukkit.scheduler.BukkitRunnable;

@EntitySize(width = 0.6F, height = 1.9F)
@EntityPetData(petType = PetType.SKELETON)
public class EntitySkeletonPetImpl extends EntityPetImpl implements EntitySkeletonPet {

    public EntitySkeletonPetImpl(World world) {
        super(world);
    }

    public EntitySkeletonPetImpl(World world, final Pet pet) {
        super(world, pet);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (((SkeletonPet) pet).isWither()) {
                    setEquipment(0, new ItemStack(Items.STONE_SWORD));
                } else {
                    setEquipment(0, new ItemStack(Items.BOW));
                }
            }
        }.runTaskLater(EchoPet.getPlugin(), 5L);
    }

    @Override
    public void setWither(boolean flag) {
        this.datawatcher.watch(13, (byte) (flag ? 1 : 0));
        if (flag) {
            setEquipment(0, new ItemStack(Items.STONE_SWORD));
        } else {
            setEquipment(0, new ItemStack(Items.BOW));
        }
    }

    public int getSkeletonType() {
        return this.datawatcher.getByte(13);
    }

    @Override
    protected void initDatawatcher() {
        super.initDatawatcher();
        this.datawatcher.a(13, new Byte((byte) 0));
    }

    @Override
    protected String getIdleSound() {
        return "mob.skeleton.say";
    }

    @Override
    protected void makeStepSound() {
        this.makeSound("mob.skeleton.step", 0.15F, 1.0F);
    }

    @Override
    protected String getDeathSound() {
        return "mob.skeleton.death";
    }

    @Override
    public SizeCategory getSizeCategory() {
        if (this.getSkeletonType() == 1) {
            return SizeCategory.LARGE;
        } else {
            return SizeCategory.REGULAR;
        }
    }
}