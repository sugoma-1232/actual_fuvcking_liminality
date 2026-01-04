package com.theo222.liminality;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

public class LiminalitySavedData extends SavedData {
    int HallucinationChance = -1;
    int Ticks;
    public static final SavedDataType<LiminalitySavedData> ID = new SavedDataType<>(
            "LiminalitySavedData",
            LiminalitySavedData::new,
            ctx -> RecordCodecBuilder.create(instance -> instance.group(
                    RecordCodecBuilder.point(ctx),
                    Codec.INT.fieldOf("hallucination_chance").forGetter(sd -> sd.HallucinationChance),
                    Codec.INT.fieldOf("ticks").forGetter(sd -> sd.Ticks)
            ).apply(instance, LiminalitySavedData::new))
    );
    public LiminalitySavedData(SavedData.Context ctx) {

    }
    public LiminalitySavedData(SavedData.Context ctx, int HallucinationChance, int Ticks) {
        this.Ticks = Ticks;
        this.HallucinationChance = HallucinationChance;
    }
    public void foo() {
        this.setDirty();
    }
}
