package ru.aaf.finshop.client.domain;

import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClientView {
    private String profileId;
    private String clientId;
    private String name;
    private String passport;
    private Integer income;

    public ClientView(String profileId) {
        this.profileId = profileId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ClientView that = (ClientView) o;
        return Objects.equals(profileId, that.profileId)
                && Objects.equals(clientId, that.clientId)
                && Objects.equals(name, that.name)
                && Objects.equals(passport, that.passport)
                && Objects.equals(income, that.income);
    }

    @Override
    public int hashCode() {
        return Objects.hash(profileId, clientId, name, passport, income);
    }

    @Override
    public String toString() {
        return "Class " + this.getClass().getSimpleName() + " {\nprofileId: " + profileId + ";\nclientId: " + clientId
                + ";\nname: " + name + ";\n" + passport + ";\nincome" + income + "\n} ";
    }
}
