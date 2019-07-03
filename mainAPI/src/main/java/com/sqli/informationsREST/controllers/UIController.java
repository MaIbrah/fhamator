package com.sqli.informationsREST.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sqli.informationsREST.repositories.InfoRepository;
import com.sqli.informationsREST.services.InfoService;

import io.swagger.annotations.Api;

@Controller
@Api(value = "CRUD for webPages", hidden = true)
@RequestMapping
public class UIController {

    private InfoRepository repository;

    @Autowired
    public UIController(InfoRepository repository) {
        this.repository = repository;
    }

    @Autowired
    private InfoService infoService;

    /* Hidden Methodes that should not be displayed in swagger */
  /*  @ApiOperation(value = "Load Insert page", hidden = true)
    @GetMapping("/InsertPage")
    public ModelAndView save() {
        return new ModelAndView("insertInformation");
    }

    @ApiOperation(value = "Load Insert page", hidden = true)
    @PostMapping("/InformationInsert")
    public ModelAndView saveInfos(@ModelAttribute Information information,
                                  @RequestParam("keys[]") List<String> keys,
                                  @RequestParam("values[]") List<String> values) {

        information.setAttributes(fillAttributes(keys, values));
        repository.insert(information);
        return new ModelAndView("redirect:/InformationsPage");
    }

    @ApiOperation(value = "Update Used with Ajax no need to show it", hidden = true)
    @PutMapping("/InformationUpdate")
    public ModelAndView updateInfos(@RequestBody Map<String, Object> data) {
        repository.save(parseInformationFromMapArray(data));
        return new ModelAndView("redirect:/InformationsPage");
    }

    @ApiOperation(value = "View a list of informations", response = Iterable.class, hidden = true)
    @GetMapping("/InformationsPage")
    public ModelAndView tableInfos(Model info, @RequestParam("page") Optional<Integer> page,
                                   @RequestParam("size") Optional<Integer> size) {
        Page<Information> informationPage = getAllInformation(page, size);
        info.addAttribute("infos", informationPage);
        info.addAttribute("pageNumbers", getPageNumbers(informationPage));
        return new ModelAndView("tableInformation");
    }

    @ApiOperation(value = "View an information that has an ID given", hidden = true)
    @GetMapping("/InformationLoad/{id}")
    public ModelAndView update(Model info, @PathVariable String id) {
        info.addAttribute("information", repository.findBy_id(id));
        return new ModelAndView("updateInformation");
    }

    @ApiOperation(value = "View a list of informations", response = Iterable.class, hidden = true)
    @GetMapping("/InformationsPage/{name}")
    public ModelAndView getFiltredTables(Model info, @RequestParam("page") Optional<Integer> page,
                                         @RequestParam("size") Optional<Integer> size, @PathVariable String name) {
        Page<Information> informationPage = getInformation(page, size, name);
        info.addAttribute("infos", informationPage);
        info.addAttribute("pageNumbers", getPageNumbers(informationPage));
        return new ModelAndView("tableInformation");
    }

    private List<Integer> getPageNumbers(Page<Information> informationPage) {
        int totalPages = informationPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
            return pageNumbers;
        }
        return Collections.EMPTY_LIST;
    }

    private Page<Information> getAllInformation(Optional<Integer> page, Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        return infoService.findPaginated(repository.findAll(), PageRequest.of(currentPage - 1, pageSize));
    }

    private Page<Information> getInformation(Optional<Integer> page, Optional<Integer> size, String name) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);
        return infoService.findPaginated(requestExecute(ELASTIC_SEARCH_LINK + name + "?size=" + pageSize + "&page=" + pageSize),
            PageRequest.of(currentPage - 1, pageSize));
    }

    private static Information parseInformationFromMapArray(Map<String, Object> data) {
        final String infoKey = "Information";
        final String mapKey = "Keys";
        final String mapValue = "Values";
        final String infoRegexSplitter = ";";

        String informationAsString = (String) data.get(infoKey);
        String[] infos = informationAsString.split(infoRegexSplitter);

        ArrayList<String> keys = (ArrayList<String>) data.get(mapKey);
        ArrayList<String> values = (ArrayList<String>) data.get(mapValue);

        return populateInformation(infos, keys, values);
    }

    private static Information populateInformation(String[] infos, List<String> keys, List<String> values) {
        Information information = new Information();
        information.set_id(infos[0]);
        information.setType(infos[1]);
        information.setName(infos[2]);
        information.setAttributes(fillAttributes(keys, values));
        return information;
    }

    private static Map<String, String> fillAttributes(List<String> keys, List<String> values) {

        return IntStream.range(0, keys.size())
            .collect(LinkedHashMap::new, (map, iterator) -> map.put(keys.get(iterator), values.get(iterator)), Map::putAll);
    }*/
}