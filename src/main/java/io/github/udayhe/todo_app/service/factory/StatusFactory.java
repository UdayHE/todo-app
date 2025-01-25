package io.github.udayhe.todo_app.service.factory;

import io.github.udayhe.todo_app.service.IStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class StatusFactory {

    private final Map<String, IStatusService> statusServices;

    @Autowired
    public StatusFactory(List<IStatusService> statusServiceList) {
        Map<String, IStatusService> tmpStatusServices = new HashMap<>();
        for (IStatusService service : statusServiceList)
            tmpStatusServices.put(service.getStatus(), service);
        this.statusServices = Collections.unmodifiableMap(tmpStatusServices);
    }

    public IStatusService get(String status) {
        return statusServices.get(status);
    }

}
