package io.anuke.mindustry.graphics;

import io.anuke.arc.graphics.*;
import io.anuke.arc.graphics.Pixmap.*;
import io.anuke.arc.graphics.Texture.*;
import io.anuke.arc.graphics.g2d.*;
import io.anuke.arc.util.*;

public class MultiPacker implements Disposable{
    private PixmapPacker[] packers = new PixmapPacker[PageType.all.length];

    public MultiPacker(){
        for(int i = 0; i < packers.length; i++){
            int pageSize = 2048;
            packers[i] = new PixmapPacker(pageSize, pageSize, Format.RGBA8888, 2, true);
        }
    }

    public boolean has(PageType type, String name){
        return packers[type.ordinal()].getRect(name) != null;
    }

    public void add(PageType type, String name, PixmapRegion region){
        packers[type.ordinal()].pack(name, region);
    }

    public void add(PageType type, String name, Pixmap pix){
        packers[type.ordinal()].pack(name, pix);
    }

    public TextureAtlas flush(TextureFilter filter, TextureAtlas atlas){
        for(PixmapPacker p : packers){
            p.updateTextureAtlas(atlas, filter, filter, false, false);
        }
        return atlas;
    }

    @Override
    public void dispose(){
        for(PixmapPacker packer : packers){
            packer.dispose();
        }
    }

    public enum PageType{
        main,
        environment,
        editor,
        zone,
        ui;

        public static final PageType[] all = values();
    }
}
