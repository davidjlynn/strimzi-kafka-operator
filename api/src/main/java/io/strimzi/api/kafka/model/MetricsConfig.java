/*
 * Copyright Strimzi authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package io.strimzi.api.kafka.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.fabric8.kubernetes.api.model.networking.v1.NetworkPolicyPeer;
import io.strimzi.crdgenerator.annotations.Description;
import io.strimzi.crdgenerator.annotations.KubeLink;
import java.util.List;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Describes the metrics configuration
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(name = JmxPrometheusExporterMetrics.TYPE_JMX_EXPORTER, value = JmxPrometheusExporterMetrics.class),
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode
public abstract class MetricsConfig implements UnknownPropertyPreserving, Serializable {

    private static final long serialVersionUID = 1L;

    private List<NetworkPolicyPeer> networkPolicyPeers;
    private Map<String, Object> additionalProperties = new HashMap<>(0);

    @Description("Metrics type. Only 'jmxPrometheusExporter' supported currently.")
    public abstract String getType();

    @Description("List of peers which should be able to connect to this listener. " +
        "Peers in this list are combined using a logical OR operation. " +
        "If this field is empty or missing, all connections will be allowed for this listener. " +
        "If this field is present and contains at least one item, the listener only allows the traffic which matches at least one item in this list.")
    @KubeLink(group = "networking.k8s.io", version = "v1", kind = "networkpolicypeer")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public List<NetworkPolicyPeer> getNetworkPolicyPeers() {
        return networkPolicyPeers;
    }

    public void setNetworkPolicyPeers(List<NetworkPolicyPeer> networkPolicyPeers) {
        this.networkPolicyPeers = networkPolicyPeers;
    }

    @Override
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @Override
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}

