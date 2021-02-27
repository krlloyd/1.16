package cofh.lib.util.filter;

public interface IFilterable {

    IFilter getFilter();

    void onFilterChanged();

}
