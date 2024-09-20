package pe.edu.upc.donfy.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.donfy.dtos.DonationONGDTO;
import pe.edu.upc.donfy.dtos.DonationSummaryDTO;
import pe.edu.upc.donfy.dtos.DonationsDTO;
import pe.edu.upc.donfy.entities.Donations;
import pe.edu.upc.donfy.serviceinterfaces.IDonationsService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Donations")
public class DonationsControllers {
    @Autowired
    private IDonationsService dC;

    @GetMapping
    public List<DonationsDTO> listar(){
        return dC.list().stream().map(x-> {
            ModelMapper m = new ModelMapper();
            return m.map(x, DonationsDTO.class);
        }).collect(Collectors.toList());
    }
    @PostMapping
    public void registar(@RequestBody DonationsDTO dto) {
        ModelMapper m = new ModelMapper();
        Donations donations = m.map(dto, Donations.class);
        dC.insert(donations);
    }
    @GetMapping("/{idDonation}")
    public DonationsDTO listarId(@PathVariable("idUsuario") Integer idDonation) {
        ModelMapper m=new ModelMapper();
        DonationsDTO dto=m.map(dC.listId(idDonation), DonationsDTO.class);
        return dto;
    }
    @PutMapping
    public void modificar(@RequestBody DonationsDTO dto) {
        ModelMapper m=new ModelMapper();
        Donations donations = m.map(dto, Donations.class);
        dC.update(donations);
    }
    @DeleteMapping("/{idDonation}")
    public void eliminar(@PathVariable("idDonation") Integer idDonation){
        dC.delete(idDonation);
    }

    @GetMapping("/FiltrarDonativosPorEstado")
    public List<DonationsDTO> FiltrarPorEstado(@RequestParam String estado)
    {
        return dC.listDonationsForYourStatus(estado).stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, DonationsDTO.class);
        }).collect(Collectors.toList());
    }

    @GetMapping("/ResumenMonetarioPorONG")
    public List<DonationSummaryDTO> TotalDonadoPorONG(@RequestParam int anio)
    {
        return dC.listOfMonetaryDonationsByONG(anio).stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, DonationSummaryDTO.class);
        }).collect(Collectors.toList());
    }

    @GetMapping("/FiltrarDonativosPorONG")
    public List<DonationsDTO> FiltrarPorONG(@RequestParam String ong)
    {
        return dC.listDonationsByONG(ong).stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, DonationsDTO.class);
        }).collect(Collectors.toList());
    }

    @GetMapping("/AsignarDonativoONG")
    public List<DonationONGDTO> FiltrarPorONG(@RequestParam int idDonativo, Long idong)
    {
        return dC.listDonationAndONGByIds(idDonativo,idong).stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, DonationONGDTO.class);
        }).collect(Collectors.toList());
    }

    @GetMapping("/FiltrarReportePorTipoDonativo")
    public List<DonationsDTO> FiltrarReportePorTipoDonativo(@RequestParam String typeDonationName)
    {
        return dC.listDonationsByDonationsType(typeDonationName).stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, DonationsDTO.class);
        }).collect(Collectors.toList());
    }
}
