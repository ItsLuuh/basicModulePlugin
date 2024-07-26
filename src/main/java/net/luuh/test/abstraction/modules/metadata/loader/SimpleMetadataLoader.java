package net.luuh.test.abstraction.modules.metadata.loader;

import net.luuh.test.abstraction.modules.metadata.Metadata;
import net.luuh.test.players.objects.User;

public interface SimpleMetadataLoader<T extends Metadata> extends MetadataLoader<T> {

    T load(User user);
}
