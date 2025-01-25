package io.github.udayhe.todo_app.service;

import java.util.Set;

public interface IStatusService {

    String getStatus();

    Boolean update(Set<String> ids);
}
