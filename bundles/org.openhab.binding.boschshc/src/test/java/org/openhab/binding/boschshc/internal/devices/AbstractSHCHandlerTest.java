/**
 * Copyright (c) 2010-2022 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.boschshc.internal.devices;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openhab.binding.boschshc.internal.devices.bridge.BridgeHandler;
import org.openhab.core.config.core.Configuration;
import org.openhab.core.thing.Bridge;
import org.openhab.core.thing.Thing;
import org.openhab.core.thing.ThingStatus;
import org.openhab.core.thing.ThingStatusDetail;
import org.openhab.core.thing.ThingStatusInfo;
import org.openhab.core.thing.ThingTypeUID;
import org.openhab.core.thing.ThingUID;
import org.openhab.core.thing.binding.ThingHandlerCallback;

/**
 * Abstract unit test implementation for all types of handlers.
 *
 * @author David Pace - Initial contribution
 *
 * @param <T> type of the handler to be tested
 */
@ExtendWith(MockitoExtension.class)
public abstract class AbstractSHCHandlerTest<T extends BoschSHCHandler> {

    private T fixture;

    @Mock
    private Thing thing;

    @Mock
    private Bridge bridge;

    @Mock
    private BridgeHandler bridgeHandler;

    @Mock
    private ThingHandlerCallback callback;

    @BeforeEach
    public void beforeEach() {
        fixture = createFixture();
        lenient().when(thing.getUID()).thenReturn(getThingUID());
        when(thing.getBridgeUID()).thenReturn(new ThingUID("boschshc", "shc", "myBridgeUID"));
        when(callback.getBridge(any())).thenReturn(bridge);
        fixture.setCallback(callback);
        when(bridge.getHandler()).thenReturn(bridgeHandler);
        when(thing.getConfiguration()).thenReturn(getConfiguration());

        fixture.initialize();
    }

    protected abstract T createFixture();

    protected T getFixture() {
        return fixture;
    }

    protected ThingUID getThingUID() {
        return new ThingUID(getThingTypeUID(), "abcdef");
    }

    protected abstract ThingTypeUID getThingTypeUID();

    protected Configuration getConfiguration() {
        return new Configuration();
    }

    protected Thing getThing() {
        return thing;
    }

    public BridgeHandler getBridgeHandler() {
        return bridgeHandler;
    }

    public ThingHandlerCallback getCallback() {
        return callback;
    }

    @Test
    public void testInitialize() {
        ThingStatusInfo expectedStatusInfo = new ThingStatusInfo(ThingStatus.ONLINE, ThingStatusDetail.NONE, null);
        verify(callback).statusUpdated(same(thing), eq(expectedStatusInfo));
    }
}
