package br.net.ricardotakemura.search.model;

import br.net.ricardotakemura.search.model.impl.StreamSearchModel;

public class StreamSearchModelTest extends AbstractSearchModelTest {

    @Override
    SearchModel getSearchModel() {
        return new StreamSearchModel();
    }
}
