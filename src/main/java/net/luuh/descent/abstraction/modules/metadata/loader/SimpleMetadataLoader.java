package net.luuh.descent.abstraction.modules.metadata.loader;

import net.luuh.descent.abstraction.modules.metadata.Metadata;
import net.luuh.descent.players.objects.User;

public interface SimpleMetadataLoader<T extends Metadata> extends MetadataLoader<T> {

    T load(User user);
}
