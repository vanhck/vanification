package de.vanhck.View;

import com.vaadin.data.HasValue;
import com.vaadin.shared.ui.slider.SliderOrientation;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Slider;
import de.vanhck.data.Option;
import de.vanhck.data.OptionDAO;
import de.vanhck.data.UserDAO;

/**
 * Created by renx on 24.06.17.
 */
public class AdminView extends ClosableView {

    private Option option;

    public AdminView(OptionDAO optionSaver, UserDAO userSaver, Layout parentLayout, Layout toClose) {
        super(parentLayout, toClose);
        if (!optionSaver.findAll().iterator().hasNext()) {
            option = new Option();
            optionSaver.save(option);
        } else {
            option = optionSaver.findAll().iterator().next();
        }

        GridLayout mainLayout = new GridLayout(4, 4);
        mainLayout.setSpacing(true);
        addComponent(mainLayout);

        Slider efficiencySlider = new Slider(0, 1, 2);
        efficiencySlider.setValue(option.getEffiency());
        efficiencySlider.setOrientation(SliderOrientation.VERTICAL);
        mainLayout.addComponent(efficiencySlider, 0, 0);

        GridLayout layout = new GridLayout(2, 6);
        layout.setSpacing(true);
        mainLayout.addComponent(layout, 1, 0);

        layout.addComponent(new Label("Effizienz"), 0, 0);
        layout.addComponent(new Label("Relevanz"), 1, 0);

        layout.addComponent(new Label("Treibstoffverbrauch"), 0, 1);
        Slider fuelSlider = new Slider(0, 1, 2);
        fuelSlider.setValue(option.getaFuelConsumption());
        fuelSlider.setOrientation(SliderOrientation.HORIZONTAL);
        layout.addComponent(fuelSlider, 1, 1);
        fuelSlider.addValueChangeListener(new HasValue.ValueChangeListener<Double>() {
            @Override
            public void valueChange(HasValue.ValueChangeEvent<Double> valueChangeEvent) {
                option.setaFuelConsumption(valueChangeEvent.getValue());
                optionSaver.save(option);
            }
        });

        layout.addComponent(new Label("Vollbremsungen"), 0, 2);
        Slider hardStopsSlider = new Slider(0, 1, 2);
        hardStopsSlider.setValue(option.getaHardStopCount());
        hardStopsSlider.setOrientation(SliderOrientation.HORIZONTAL);
        layout.addComponent(hardStopsSlider, 1, 2);
        hardStopsSlider.addValueChangeListener(new HasValue.ValueChangeListener<Double>() {
            @Override
            public void valueChange(HasValue.ValueChangeEvent<Double> valueChangeEvent) {
                option.setaHardStopCount(valueChangeEvent.getValue());
                optionSaver.save(option);
            }
        });

        layout.addComponent(new Label("Harte Beschleunigungen"), 0, 3);
        Slider hardAcclsSlider = new Slider(0, 1, 2);
        hardAcclsSlider.setValue(option.getaHardAccelerationCount());
        hardAcclsSlider.setOrientation(SliderOrientation.HORIZONTAL);
        layout.addComponent(hardAcclsSlider, 1, 3);
        hardAcclsSlider.addValueChangeListener(new HasValue.ValueChangeListener<Double>() {
            @Override
            public void valueChange(HasValue.ValueChangeEvent<Double> valueChangeEvent) {
                option.setaHardAccelerationCount(valueChangeEvent.getValue());
                optionSaver.save(option);
            }
        });

        layout.addComponent(new Label("Seitwärtsbeschleunigung"), 0, 4);
        Slider sideWaySlider = new Slider(0, 1, 2);
        sideWaySlider.setValue(option.getaSidewaysAcceleration());
        sideWaySlider.setOrientation(SliderOrientation.HORIZONTAL);
        layout.addComponent(sideWaySlider, 1, 4);
        sideWaySlider.addValueChangeListener(new HasValue.ValueChangeListener<Double>() {
            @Override
            public void valueChange(HasValue.ValueChangeEvent<Double> valueChangeEvent) {
                option.setaSidewaysAcceleration(valueChangeEvent.getValue());
                optionSaver.save(option);
            }
        });

        layout.addComponent(new Label("Gleichbleibende Geschwindigkeit"), 0, 5);
        Slider constVeloSlider = new Slider(0, 1, 2);
        constVeloSlider.setValue(option.getaConstantVelocity());
        constVeloSlider.setOrientation(SliderOrientation.HORIZONTAL);
        layout.addComponent(constVeloSlider, 1, 5);
        constVeloSlider.addValueChangeListener(new HasValue.ValueChangeListener<Double>() {
            @Override
            public void valueChange(HasValue.ValueChangeEvent<Double> valueChangeEvent) {
                option.setaConstantVelocity(valueChangeEvent.getValue());
                optionSaver.save(option);
            }
        });

        Slider productivitySlider = new Slider(0, 1, 2);
        productivitySlider.setValue(option.getProductivity());
        productivitySlider.setOrientation(SliderOrientation.VERTICAL);
        mainLayout.addComponent(productivitySlider, 0, 1);

        GridLayout layout2 = new GridLayout(2, 2);
        layout2.setSpacing(true);
        mainLayout.addComponent(layout2, 1, 1);

        layout2.addComponent(new Label("Produktivität"), 0, 0);
        layout2.addComponent(new Label("Wert"), 1, 0);

        layout2.addComponent(new Label("Optimale Anzahl an Stops pro 10 km"), 0, 1);
        Slider stopSlider = new Slider(0, 100);
        stopSlider.setValue((double)option.getEStops());
        stopSlider.setOrientation(SliderOrientation.HORIZONTAL);
        layout2.addComponent(stopSlider, 1, 1);
        stopSlider.addValueChangeListener(new HasValue.ValueChangeListener<Double>() {
            @Override
            public void valueChange(HasValue.ValueChangeEvent<Double> valueChangeEvent) {
                option.setEStops(valueChangeEvent.getValue().intValue());
                optionSaver.save(option);
            }
        });

        efficiencySlider.addValueChangeListener(new HasValue.ValueChangeListener<Double>() {
            @Override
            public void valueChange(HasValue.ValueChangeEvent<Double> valueChangeEvent) {
                if (Math.abs(valueChangeEvent.getValue() + productivitySlider.getValue() - 1) > 0.00001) {
                    productivitySlider.setValue(1 - valueChangeEvent.getValue());
                    option.setProductivity(1 - valueChangeEvent.getValue());
                    option.setEffiency(valueChangeEvent.getValue());
                    optionSaver.save(option);
                }
            }
        });

        productivitySlider.addValueChangeListener(new HasValue.ValueChangeListener<Double>() {
            @Override
            public void valueChange(HasValue.ValueChangeEvent<Double> valueChangeEvent) {
                if (Math.abs(valueChangeEvent.getValue() + efficiencySlider.getValue() - 1) > 0.00001) {
                    efficiencySlider.setValue(1 - valueChangeEvent.getValue());
                    option.setEffiency(1 - valueChangeEvent.getValue());
                    option.setProductivity(valueChangeEvent.getValue());
                    optionSaver.save(option);
                }
            }
        });
    }
}
