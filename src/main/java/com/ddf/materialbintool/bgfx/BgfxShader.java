package com.ddf.materialbintool.bgfx;

import com.ddf.materialbintool.util.ByteBufUtil;
import com.ddf.materialbintool.util.IData;
import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;

public abstract class BgfxShader implements IData {
    private int magic;
    private int hash;
    private List<Uniform> uniforms;
    private transient byte[] code;

    public static BgfxShader create(String platform) {
        if (platform.startsWith("Direct3D"))
            return new BgfxShaderD3D();
        if (platform.startsWith("GLSL") || platform.startsWith("ESSL"))
            return new BgfxShaderGL();

        throw new RuntimeException("Unsupported platform: " + platform);
    }

    public static Class<? extends BgfxShader> getClass(String platform) {
        if (platform.startsWith("Direct3D"))
            return BgfxShaderD3D.class;
        if (platform.startsWith("GLSL") || platform.startsWith("ESSL"))
            return BgfxShaderGL.class;

        throw new RuntimeException("Unsupported platform: " + platform);
    }

    @Override
    public void read(ByteBuf buf) {
        magic = buf.readInt();
        hash = buf.readIntLE();

        short count = buf.readShortLE();
        uniforms = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Uniform uniform = new Uniform();
            uniform.readFrom(buf);
            uniforms.add(uniform);
        }

        code = ByteBufUtil.readByteArray(buf);
        buf.readByte(); //0
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeInt(magic);
        buf.writeIntLE(hash);

        buf.writeShortLE(uniforms.size());
        for (Uniform uniform : uniforms) {
            uniform.writeTo(buf);
        }

        ByteBufUtil.writeByteArray(buf, code);
        buf.writeByte(0);
    }

    public int getMagic() {
        return magic;
    }

    public void setMagic(int magic) {
        this.magic = magic;
    }

    public int getHash() {
        return hash;
    }

    public void setHash(int hash) {
        this.hash = hash;
    }

    public List<Uniform> getUniforms() {
        return uniforms;
    }

    public void setUniforms(List<Uniform> uniforms) {
        this.uniforms = uniforms;
    }

    public byte[] getCode() {
        return code;
    }

    public void setCode(byte[] code) {
        this.code = code;
    }
}