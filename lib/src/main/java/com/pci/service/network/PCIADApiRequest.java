package com.pci.service.network;




public class PCIADApiRequest {

    private final PCIADApiTarget target;
    private final Object data;


    PCIADApiRequest(final  PCIADApiTarget target,  final Object data) {
        this.target = target;
        this.data = data;
    }


    public PCIADApiTarget getTarget() {
        return target;
    }
    public Object getData() {
        return data;
    }



    public String description() {
        switch (target) {
            case PCIAD_1002_CHECK_IN:
                return "DMR 체크인 전송";
            case PCIAD_1004_CHECK_IN_LIST:
                return "체크인리스트 요청";
        }

        return null;
    }
}
