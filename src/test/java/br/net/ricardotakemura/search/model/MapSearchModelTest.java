package br.net.ricardotakemura.search.model;

import br.net.ricardotakemura.search.model.impl.MapSearchModel;

public class MapSearchModelTest extends AbstractSearchModelTest {
    @Override
    SearchModel getSearchModel() {
        return new MapSearchModel();
    }
}
