package com.robertx22.mine_and_slash.database.data.currency.reworked;

import java.util.ArrayList;
import java.util.List;

public class ExileCurrency {


    String id;

    List<ItemModification> modifications = new ArrayList<>();

    List<ItemRequirement> req = new ArrayList<>();


    public class ExileCurrencyBuilder {

        private List<ItemModification> modifications = new ArrayList<>();
        private List<ItemRequirement> req = new ArrayList<>();
        String id;

        public ExileCurrencyBuilder(String id) {
            this.id = id;
        }

        public ExileCurrencyBuilder addModification(ItemModification modification) {
            this.modifications.add(modification);
            return this;
        }

        public ExileCurrencyBuilder addRequirement(ItemRequirement requirement) {
            this.req.add(requirement);
            return this;
        }

        public ExileCurrency build() {
            ExileCurrency currency = new ExileCurrency();
            currency.modifications = this.modifications;
            currency.req = this.req;
            currency.id = id;
            return currency;
        }
    }
}
