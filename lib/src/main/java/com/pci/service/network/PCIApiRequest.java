package com.pci.service.network;


import com.pci.service.redux.state.PCIState;

public class PCIApiRequest {
    private final PCIApiTarget target;
    private final Object data;
    private final PCIState state;

    PCIApiRequest(final  PCIApiTarget target,  final Object data,final PCIState state) {
        this.target = target;
        this.data = data;
        this.state = PCIState.from(state);
    }


    public PCIApiTarget getTarget() {
        return target;
    }
    public Object getData() {
        return data;
    }
    public PCIState getState() {
        return state;
    }
    public String description() {
        switch (target) {
            case PCI_3001_UPLOAD_TERM_AGREEMENTS:
                return "약관 동의/철회 정보 전송";
            case PCI_3002_FETCH_POLICY:
                return "SDK 정책 조회";
            case PCI_3018_UPLOAD_MIC_PERMISSION_AGREEMENTS:
                return "근처기기 권한 조회";
            case PCI_3026_DMR_CHECK_IN:
                return "DMR체크인";
            case PCI_3003_CHECK_IN:
                return "체크인";
            case PCI_3024_CHECK_IN_LIST:
                return "체크인리스트 조회";
        }

        return null;
    }
}
