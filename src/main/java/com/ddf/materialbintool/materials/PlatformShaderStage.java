package com.ddf.materialbintool.materials;

import com.ddf.materialbintool.util.ByteBuf;

import java.util.Objects;

public class PlatformShaderStage {
    public String typeName;
    public String platformName;
    public ShaderCodeType type;
    public ShaderCodePlatform platform;

    public PlatformShaderStage() {}

    public PlatformShaderStage(ShaderCodeType type, ShaderCodePlatform platform) {
        this.type = type;
        this.platform = platform;
        this.typeName = type.name();
        this.platformName = platform.name();
    }

    public void read(ByteBuf buf) {
        typeName = buf.readStringLE();
        platformName = buf.readStringLE();
        type = ShaderCodeType.get(buf.readByte());
        platform = ShaderCodePlatform.get(buf.readByte());
    }

    public void write(ByteBuf buf) {
        buf.writeStringLE(typeName);
        buf.writeStringLE(platformName);
        buf.writeByte(type.ordinal());
        buf.writeByte(platform.ordinal());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlatformShaderStage that = (PlatformShaderStage) o;
        return type == that.type && platform == that.platform;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, platform);
    }

    @Override
    public String toString() {
        return "PlatformShaderStage{" +
                "typeName='" + typeName + '\'' +
                ", platformName='" + platformName + '\'' +
                ", type=" + type +
                ", platform=" + platform +
                '}';
    }
}
