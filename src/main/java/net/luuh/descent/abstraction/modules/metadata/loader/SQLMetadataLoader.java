package net.luuh.descent.abstraction.modules.metadata.loader;

import net.luuh.descent.abstraction.modules.metadata.Metadata;
import net.luuh.descent.players.objects.User;

import java.sql.Connection;

public interface SQLMetadataLoader<T extends Metadata> extends MetadataLoader<T> {

    T load(User user, Connection connection);
}
